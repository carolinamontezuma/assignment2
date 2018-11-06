package data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Content implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String[] Categories = { "Comedy", "Sci-Fi", "Horror", "Romance", "Action", "Thriller", "Drama",
			"Mistery", "Crime", "Animation", "Adventure", "Fantasy", "Romantic comedy", "Action comedy", "Superhero",
			"History", "Documentary", "Musical", "Sport", "Family", "Biography", "War", "Western" };

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int id;
	private String title;
	private String director;
	private int year;
	private String category;

	public Content() {
		super();
	}

	public Content(String title, String director, int year, String category) {
		super();
		this.title = title;
		this.director = director;
		this.year = year;
		this.category = category;
	}

	public int getID() {
		return this.id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String Director() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return " title = " + this.title + " director = " + this.director + " year = " + this.year + "category = "
				+ this.category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Content other = (Content) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
}