package conexion;
import java.sql.*;

/**
 * Ejemplo de uso de JDBC:
 * -Se establece la cadena de conexión con la base de datos Libros
 * -Se obtienen todos los libros del autor cuyo codigo=5
 * -Se muestran los datos por pantalla 
 */
public class ConsultaStatement {

   public static void main(String[] args) {
      try {
         Conexion.setURL("jdbc:mysql://localhost:3306/Libros?user=usuario1&password=usuario1");
         Statement sentenciaSQL = Conexion.getConexion().createStatement();
          
         StringBuffer sb = new StringBuffer();
         
         sb.append("SELECT * from libros ");
         sb.append("WHERE titulo LIKE '%java%';");
      
         System.out.println(sb.toString());
         
         ResultSet res =sentenciaSQL.executeQuery(sb.toString());
         
         
         String codigolibro;
         String isbn;
         String titulo;
         String editorial;
         String fecha;
         res.beforeFirst();
         
         while (res.next()) {
         	System.out.println("\nCODIGO\tISBN\t\tTITULO");
         	
         	codigolibro = res.getString("codigolibro").toString();
         	isbn 		= res.getString("isbn").toString();
         	titulo 		= res.getString("titulo").toString();
         	System.out.println(codigolibro+"\t"+isbn+"\t"+titulo);
         	
         	System.out.println("EDITORIAL\tFECHA");
         	
         	editorial = res.getString("editorial").toString();
         	fecha = res.getString("fecha").toString();
         	System.out.println(editorial+"\t"+fecha);
         	
         	System.out.println("-----------------------");
         }
         
			Conexion.desconecta();
         
      } catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
      }
   }
}