package pccomparator;

public class Item {
	private String nombre;
	private double precio;
	private String pagina;
	private Boolean disponible;
	public Item(String nombre,double precio,boolean disponible,String pagina) {
		this.pagina=pagina;
		this.nombre=nombre;
		this.precio=precio;
		this.disponible=disponible;
	}
	public Boolean getDisponible() {
		return disponible;
	}
	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
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
