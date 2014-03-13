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
	
	static {Conexion.setURL("jdbc:mysql://localhost:3306/Libros?user=usuario1&password=usuario1");}
	static Statement sentenciaSQL;

	public static void main(String[] args) {

		String consulta = "SELECT * from libros;";
		
		/** 1 Lanza consulta, devuele un resulset, obtiene y guarda nformacion del resultset*/
		res = lanzarSelect(consulta);
		
		/**2 muestra el contenido del resulset obtenido */
		mostrarSelect(res);
		
		/**3 obtiene nuevo Id de la tabla libros*/
		int nuevoId=getNuevoID("libros", "codigoLibro");
		
		System.out.println("El nuevo ID es "+nuevoId);
		
		/**4 permite insertar o actualizar la tabla por medio de un String orden
		 * filasAfectadas.- muestra el numero de filas que ha insertado o actualizado*/
		String actualizaGrabaFila = "INSERT INTO libros VALUES ("+nuevoId+",0-777-88899-9,'La vida de PI','Melonia','1900-04-12')";//||"UPDATE Libros SET Fecha='2002-01-31' WHERE CodigoLibro=1;"
		final int filasAfectadas;
		
		
		filasAfectadas=grabarFila(actualizaGrabaFila);
		System.out.println(filasAfectadas+" Fila(s) afectadas");
		
		/**5 añade nuevo autor a la tabla autores */
		String consulta2 = "SELECT * from autores;";
		res = lanzarSelect(consulta);
		
		
		/**cierra conexion con la db*/
		Conexion.desconecta();
	}

	/*1 Lanza consulta, devuele un resulset, obtiene y guarda nformacion del resultset  */
	private static ResultSet lanzarSelect(String select) {

		ResultSet res0=null;
		
		try {
			sentenciaSQL = Conexion.getConexion().createStatement();
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
	/*3 obtiene nuevo Id de la tabla libros*/
	private static int getNuevoID(String tabla, String ID){
		Integer id=null;
		//infoTabla.getCatalogName(column)
		try {
			res.last();
			id=res.getInt(ID);
			
			System.out.println("\nEl ultimo elemento es ->"+id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id+1;
	}
	/*4 permite insertar o actualizar la tabla por medio de un String orden*/
	private static int grabarFila(String orden){
		int filas=-1;
		
		try {
			filas=sentenciaSQL.executeUpdate(orden);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filas;
	}
}