package ltbank.model.personage;

public class PersonageDTO {

	private long id;
	private String name;
	
	public PersonageDTO() {
		
	}
	public PersonageDTO(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
