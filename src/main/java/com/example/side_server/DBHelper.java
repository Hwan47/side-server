package com.example.side_server;

import java.sql.*;
import java.util.ArrayList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 1cesa
 */
public class DBHelper {
    static DBHelper instance = null;
    static DBHelper getInstance(){
        if(instance == null)
            instance = new DBHelper();
        return instance;
    }
    public DBHelper() {
        openConnection();
    }
    
    Connection conn;
    private void openConnection() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://pjr-side.mysql.database.azure.com:3306/pjr_side?useSSL=true";
            conn = DriverManager.getConnection(url, "test", "1234");
            System.out.println("연결 성공");

        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("드라이버 로딩 실패");
        }
        catch(SQLException e){
            System.out.println("에러: " + e);
        }
        finally{
        }
    }
    public void close(Statement stmt){
         try{
                    stmt.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
    }
    
    public boolean execute(String sql){
        Statement stmt=null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(e);
            return false;
        } finally{
        close(stmt);
     }
    }
    public int executeUpdate(String sql){
            Statement stmt=null;
            try {
                stmt = conn.createStatement();
                return stmt.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.print(e.getMessage());
                return -1;
            } finally{
            close(stmt);
          }
        }

        public ArrayList<String> executeQuery(String sql, ArrayList<String> list){

            Statement stmt=null;

            ResultSet rs=null;


            try {

                stmt = conn.createStatement();

                rs = stmt.executeQuery(sql);

                while(rs.next()){
                    list.add(rs.getString(1));
                }


            } catch (Exception e) {

                //System.out.println(e.getMessage());

                e.printStackTrace();

            } finally {

                try { if(rs != null) rs.close();  } catch (Exception e) {    }

                try { if(stmt != null) stmt.close();  } catch (Exception e) {    }

            }
            return list;
        }
        public String executeQuery(String sql) {

        Statement stmt=null;

        ResultSet rs=null;


        try {

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);

            if(rs.next()){
                return rs.getString(1);
            }
            else
                return "no_data";


        } catch (Exception e) {

            //System.out.println(e.getMessage());

            e.printStackTrace();
            return "error";

        } finally {

            try { if(rs != null) rs.close();  } catch (Exception e) {    }

            try { if(stmt != null) stmt.close();  } catch (Exception e) {    }

        }
    }
     public void close(ResultSet rs){
         try{
                    rs.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
    }
}
