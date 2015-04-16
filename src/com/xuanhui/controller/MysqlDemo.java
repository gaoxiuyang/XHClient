package com.xuanhui.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlDemo {
 public static void main(String[] args) {
  Connection conn = null;
  Statement stmt = null;
  ResultSet rs = null;
  String url = null;
  String user = null;
  String password = null;
  String sql = null;
  try {
   Class.forName("com.mysql.jdbc.Driver"); //加载mysq驱动
  } catch (ClassNotFoundException e) {
   System.out.println("驱动加载错误");
   e.printStackTrace();//打印出错详细信息
  }
  try {
   url = 
    "jdbc:mysql://localhost/xhdb?user=root&password=1125&useUnicode=true&&characterEncoding=gb2312&autoReconnect = true";//简单写法：url = "jdbc:myqsl://localhost/test(数据库名)? user=root(用户)&password=yqs2602555(密码)";
   user = "root";
   password = "1126";
   conn = DriverManager.getConnection(url,user,password);
  } catch (SQLException e) {
   System.out.println("数据库链接错误");
   e.printStackTrace();
  }
  try {
   stmt = conn.createStatement();
   sql = "SELECT user_name FROM `user`WHERE bus_type = '50M'";//dept这张表有deptno，deptname和age这三个字段
   rs = stmt.executeQuery(sql);//执行sql语句
   while(rs.next()) {
   // System.out.print(rs.getInt("deptno") + "   ");
    System.out.print(rs.getString("user_name") + "   ");
   // System.out.println(rs.getInt("age") + "   ");
   }
  } catch (SQLException e) {
   System.out.println("数据操作错误");
   e.printStackTrace();
  }
//关闭数据库
  try {
   if(rs != null) {
    rs.close();
    rs = null;
   }
   if(stmt != null) {
    stmt.close();
    stmt = null;
   }
   if(conn != null) {
    conn.close();
    conn = null;
   }
  } catch(Exception e) {
   System.out.println("数据库关闭错误");
   e.printStackTrace();
  }
 }
}