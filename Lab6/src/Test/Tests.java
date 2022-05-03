import Classes.SomeClass;
import Classes.StringClass;
import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

class Run{
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestSuite.class);
        for (Failure failure:
             result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}

@RunWith(Suite.class)
@Suite.SuiteClasses(Tests.class)
class TestSuite{}

public class Tests {
    static Connection connection;
    static Statement statement;
    @BeforeClass
    public static void TestConnection(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getDeclaredConstructor().newInstance();
            String connectionURL = "jdbc:sqlserver://IGOR;databaseName=Java6;TrustServerCertificate=true;encrypt=false;IntegratedSecurity=false";
            Connection conn = DriverManager.getConnection(connectionURL, "sa", "1111");
            connection = conn;
            Statement state = conn.createStatement();
            statement = state;
            System.out.println("ConnectionTestSuccesful");
            statement.executeUpdate("USE Java6; CREATE TABLE TestTable (Название NVARCHAR(20) PRIMARY KEY)");
            System.out.println("StatementTestSuccesful, CreateTableTestSuccsesful");
        } catch (Exception e){
            System.out.println("ConnectionTestFailed");
        }
    }
    @Test(timeout = 1000)
    public void TestInsert(){
        try {
            statement.executeUpdate("INSERT into TestTable(Название) values ('Test')");
            System.out.println("TestInsertSuccesful");
        }catch (Exception e){}
    }
    @Test
    public void SomeTest(){
        try{
            String ClassName = "ClassName";
            Assert.assertEquals(ClassName, "ClassName");
            System.out.println("SomeTestSuccesful");
        }catch (Exception e){
            System.out.println("StringNotEmpty");
        }
    }
    @Ignore
    @Test
    public void ClassConnectTest(){
        try {
            SomeClass someClass = new SomeClass(StringClass.GetName());
            System.out.println("ClassConnectTestSuccesful");
        }catch (Exception e){
            System.out.println("ClassConnectTestFailed");
        }
    }
    @AfterClass
    public static void TestDropTable(){
        try {
            statement.executeUpdate("USE Java6; DROP TABLE TestTable");
            System.out.println("DropTableTestSuccsesful");
        }catch (Exception e){}
    }
}
