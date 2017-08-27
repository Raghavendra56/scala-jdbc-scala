import java.sql.DriverManager
import com.typesafe.config.ConfigFactory

case class EmployeesCommission(customer_id: Int,
                               customer_fname: String,
                               customer_lname: String,
                               customer_email: String) {
  override def toString(): String = {
    s"Customer ID: " + customer_id + ";" +"Customer First Name: " + customer_fname + ";"+
      "Customer Last Name: " + customer_lname + ";" + "Customer Email: " + customer_email + ";"
  }
}
object commissionAmount {

  def main(args: Array[String]): Unit = {
    val props = ConfigFactory.load()
    val driver = "com.mysql.jdbc.Driver"
    val host = props.getConfig(args(0)).getString("host")
    val port = props.getConfig(args(0)).getString("port")
    val db = props.getConfig(args(0)).getString("db")
    val url = "jdbc:mysql://" + host + ":" + port + "/" + db
    val username = props.getConfig(args(0)).getString("user")
    val password = props.getConfig(args(0)).getString("password")

    Class.forName(driver);
    val connection = DriverManager.getConnection(url,username,password)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT customer_id, customer_fname, customer_lname, " +
    "customer_email  FROM customers")

    while( resultSet.next()) {
      val e = EmployeesCommission(resultSet.getInt("customer_id"),
        resultSet.getString("customer_fname"),
        resultSet.getString("customer_lname"),
        resultSet.getString("customer_email"))
      println(e)
    }

  }

}
