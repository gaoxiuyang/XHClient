package com.xuanhui.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
 
 
public class MysqlDemo {
	    /**
	     * 入口函数
	     * @param arg
	     */
	    public static void main(String arg[]) {
	        try {
	            Connection con = null; //定义一个MYSQL链接对象
	            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance(); //MYSQL驱动
	            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/xhdb", "root", "1126"); //链接本地MYSQL

	            Statement stmt; //创建声明
	            stmt = con.createStatement();

//	            //新增一条数据
//	            stmt.executeUpdate("INSERT INTO user (username, password) VALUES ('init', '123456')");
//	            ResultSet res = stmt.executeQuery("select LAST_INSERT_ID()");
//	            int ret_id;
//	            if (res.next()) {
//	                ret_id = res.getInt(1);
//	                System.out.print(ret_id);
//	            }
//
//	            //删除一条数据
//	            String sql = "DELETE FROM user WHERE id = 1";
//	            long deleteRes = stmt.executeUpdate(sql); //如果为0则没有进行删除操作，如果大于0，则记录删除的条数
//	            System.out.print("DELETE:" + deleteRes);
//
//	            //更新一条数据
//	            String updateSql = "UPDATE user SET username = 'xxxx' WHERE id = 2";
//	            long updateRes = stmt.executeUpdate(updateSql);
//	            System.out.print("UPDATE:" + updateRes);

	            //查询数据并输出
	            String selectSql = "SELECT `user_name` FROM user WHERE bus_type = '50M'";
	            ResultSet selectRes = stmt.executeQuery(selectSql);
	            while (selectRes.next()) { //循环输出结果集
	                String username = selectRes.getString("user_name");
	               // String password = selectRes.getString("password");
	                System.out.print("\r\n\r\n");
	                System.out.print("username:" + username );
	            }

	        } catch (Exception e) {
	            System.out.print("MYSQL ERROR:" + e.getMessage());
	        }

	    }
	
 
}