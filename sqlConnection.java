import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
 
import net.sourceforge.jtds.jdbc.*;
 
public void query2()
{
Log.i("Android"," MySQL Connect Example.");
Connection conn = null;
try {
String driver = "net.sourceforge.jtds.jdbc.Driver";
Class.forName(driver).newInstance();
//test = com.microsoft.sqlserver.jdbc.SQLServerDriver.class;
String connString = "jdbc:jtds:sqlserver://server_ip_address :1433/DBNAME;encrypt=fasle;user=xxxxxxxxx;password=xxxxxxxx;instance=SQLEXPRESS;";
String username = "xxxxxx";
String password = "xxxxxxxxxx";
conn = DriverManager.getConnection(connString,username,password);
Log.w("Connection","open");
Statement stmt = conn.createStatement();
ResultSet reset = stmt.executeQuery("select * from TableName");
 
//Print the data to the console
while(reset.next()){
Log.w("Data:",reset.getString(3));
//              Log.w("Data",reset.getString(2));
}
conn.close();
 
} catch (Exception e)
{
Log.w("Error connection","" + e.getMessage());
}
}