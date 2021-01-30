package it.giannipeg.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="baseimages")
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class BaseImage {
    
    @Id
    @Column(name = "name")
    private String name;

    //@NaturalId
    @Size(max = 100)
    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "thumb")
    private byte[] thumb;
    
    @Lob
    @Column(name = "preview")
    private byte[] preview;
    
    @Column(name = "date")
    private LocalDate date;
    
    @Fetch(FetchMode.JOIN)
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "baseImages")
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();

	
	public BaseImage() {};
	  
	public BaseImage(String name, String type, byte[] thumb, byte[] preview, LocalDate date) { 
		this.name = name; this.type = type; this.thumb = thumb; this.preview = preview; this.date = date;
	}
	 
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getThumb() {
		return thumb;
	}
	public void setThumb(byte[] thumb) {
		this.thumb = thumb;
	}

	public byte[] getPreview() {
		return preview;
	}

	public void setPreview(byte[] preview) {
		this.preview = preview;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	@JsonIgnore
	public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseImage baseImage = (BaseImage) o;
        return Objects.equals(name, baseImage.name);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
