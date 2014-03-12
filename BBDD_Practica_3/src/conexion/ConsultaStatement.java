package conexion;

import java.sql.*;

/**
 * Ejemplo de uso de JDBC: -Se establece la cadena de conexión con la base de
 * datos Libros -Se obtienen todos los libros del autor cuyo codigo=5 -Se
 * muestran los datos por pantalla
 */
public class ConsultaStatement {

	static ResultSet res=null;//atributo para guardar el resulset
	static ResultSetMetaData infoTabla=null;//atributo para guardar la informacino de la tabla
	
	static {
		Conexion.setURL("jdbc:mysql://localhost:3306/Libros?user=usuario1&password=usuario1");
	}
	

	public static void main(String[] args) {

		String consulta = "SELECT * from libros WHERE titulo LIKE '%java%';";
		
		/* 1 Lanza consulta, devuele un resulset, obtiene y guarda nformacion del resultset*/
		res = lanzarSelect(consulta);
		
		/*2 muestra el contenido del resulset obtenido */
		mostrarSelect(res);
		
		/*3 obtiene nuevo Id de la tabla libros*/
		int nuevoId=getNuevoID("libros", "codigoLibro");
		
		System.out.println(nuevoId);
		
		/*cierra conexion con la db*/
		Conexion.desconecta();
	}

	/*1 Lanza consulta, devuele un resulset, obtiene y guarda nformacion del resultset  */
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
		
		try {
			infoTabla=res.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res0;
	}
	
	/*2 muestra el contenido del resulset obtenido */
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
	private static int getNuevoID(String tabla, String ID){
		Integer id=null;
		try {
			res.last();
			id=res.getInt(ID);
			
			System.out.println("\nEl Elemento es ->"+id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id+1;
	}
}