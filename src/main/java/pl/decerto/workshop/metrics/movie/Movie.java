package pl.decerto.workshop.metrics.movie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Movie {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public Movie() {
	}

	public Movie(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
