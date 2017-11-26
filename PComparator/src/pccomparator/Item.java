package pccomparator;

public class Item {
	private String nombre;
	private double precio;
	private String pagina;
	public Item(String nombre,double precio,String pagina) {
		this.pagina=pagina;
		this.nombre=nombre;
		this.precio=precio;
	}
	public String getPagina() {
		return pagina;
	}
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
