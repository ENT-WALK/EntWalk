package com.app.mymap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class JavaMysqlSelect extends AppCompatActivity
{
   @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_mysql_select);
    }

/*

    public static void main(String[] args)
    {
        try
        {
            // create our mysql database connection
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://127.0.0.1/entwalk";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "1234");

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT * FROM leaderboard";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("user");
                int numPoints = rs.getInt("score");
                // print the results
                System.out.format("%s, %s, %s, \n", id, name, numPoints);
            }


            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

*/

}