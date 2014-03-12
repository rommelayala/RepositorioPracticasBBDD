package conexion;

import java.sql.*;

/**
 * Ejemplo de uso de JDBC: -Se establece la cadena de conexión con la base de
 * datos Libros -Se obtienen todos los libros del autor cuyo codigo=5 -Se
 * muestran los datos por pantalla
 */
public class ConsultaStatement {

	static {
		Conexion.setURL("jdbc:mysql://localhost:3306/Libros?user=usuario1&password=usuario1");
	}

	public static void main(String[] args) {

		String consulta = "SELECT * from libros WHERE titulo LIKE '%java%';";
		
		ResultSet res = lanzarSelect(consulta);
		
		mostrarSelect(res);

		Conexion.desconecta();
	}

	private static ResultSet lanzarSelect(String select) {

		ResultSet res0=null;
		
		try {
			Statement sentenciaSQL = Conexion.getConexion().createStatement();
			res0 = sentenciaSQL.executeQuery(select);
			res0.beforeFirst();
			return res0;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res0;
	}
	
	private static void mostrarSelect(ResultSet res){
		
		String codigolibro;
		String isbn;
		String titulo;
		String editorial;
		String fecha;
		
		try {
			while (res.next()) {
			System.out.println("\nCODIGO\tISBN\t\tTITULO");

			codigolibro = res.getString("codigolibro").toString();
			isbn = res.getString("isbn").toString();
			titulo = res.getString("titulo").toString();
			System.out.println(codigolibro + "\t" + isbn + "\t" + titulo);

			System.out.println("EDITORIAL\tFECHA");

			editorial = res.getString("editorial").toString();
			fecha = res.getString("fecha").toString();
			System.out.println(editorial + "\t" + fecha);

			System.out.println("-----------------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}