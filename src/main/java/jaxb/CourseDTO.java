
package jaxb;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="Author" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="AuthorDescription" type="{www.Course.com}String" minOccurs="0"/>
 *         &lt;element name="DomainId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="CategoryId" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="OverviewDescription" type="{www.Course.com}String"/>
 *         &lt;element name="AuthorImageURL" type="{www.Course.com}URL" minOccurs="0"/>
 *         &lt;element name="Rating">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RatingNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Cost" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="Syllabus" type="{www.Course.com}String"/>
 *         &lt;element name="PreviewVideoURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImageURL" type="{www.Course.com}URL"/>
 *         &lt;element name="SourceURL" type="{www.Course.com}URL"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "name",
    "author",
    "authorDescription",
    "domainId",
    "categoryId",
    "overviewDescription",
    "authorImageURL",
    "rating",
    "ratingNumber",
    "cost",
    "duration",
    "syllabus",
    "previewVideoURL",
    "imageURL",
    "sourceURL"
})
@XmlRootElement(name = "Course", namespace = "www.Course.com")
public class CourseDTO {

    @XmlElement(name = "Id", namespace = "www.Course.com", required = false)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int id;
    @XmlElement(name = "Name", namespace = "www.Course.com", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String name;
    @XmlElement(name = "Author", namespace = "www.Course.com", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String author;
    @XmlElement(name = "AuthorDescription", namespace = "www.Course.com")
    protected String authorDescription;
    @XmlElement(name = "DomainId", namespace = "www.Course.com", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int domainId;
    @XmlElement(name = "CategoryId", namespace = "www.Course.com", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int categoryId;
    @XmlElement(name = "OverviewDescription", namespace = "www.Course.com", required = true)
    protected String overviewDescription;
    @XmlElement(name = "AuthorImageURL", namespace = "www.Course.com")
    protected String authorImageURL;
    @XmlElement(name = "Rating", namespace = "www.Course.com", required = true)
    protected BigDecimal rating;
    @XmlElement(name = "RatingNumber", namespace = "www.Course.com")
    protected int ratingNumber;
    @XmlElement(name = "Cost", namespace = "www.Course.com", required = true)
    protected BigDecimal cost;
    @XmlElement(name = "Duration", namespace = "www.Course.com", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int duration;
    @XmlElement(name = "Syllabus", namespace = "www.Course.com", required = true)
    protected String syllabus;
    @XmlElement(name = "PreviewVideoURL", namespace = "www.Course.com")
    protected String previewVideoURL;
    @XmlElement(name = "ImageURL", namespace = "www.Course.com", required = true)
    protected String imageURL;
    @XmlElement(name = "SourceURL", namespace = "www.Course.com", required = true)
    protected String sourceURL;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link int }
     *     
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
     *     
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Gets the value of the authorDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorDescription() {
        return authorDescription;
    }

    /**
     * Sets the value of the authorDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorDescription(String value) {
        this.authorDescription = value;
    }

    /**
     * Gets the value of the domainId property.
     * 
     * @return
     *     possible object is
     *     {@link int }
     *     
     */
    public int getDomainId() {
        return domainId;
    }

    /**
     * Sets the value of the domainId property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
     *     
     */
    public void setDomainId(int value) {
        this.domainId = value;
    }

    /**
     * Gets the value of the categoryId property.
     * 
     * @return
     *     possible object is
     *     {@link int }
     *     
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the value of the categoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
     *     
     */
    public void setCategoryId(int value) {
        this.categoryId = value;
    }

    /**
     * Gets the value of the overviewDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverviewDescription() {
        return overviewDescription;
    }

    /**
     * Sets the value of the overviewDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverviewDescription(String value) {
        this.overviewDescription = value;
    }

    /**
     * Gets the value of the authorImageURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorImageURL() {
        return authorImageURL;
    }

    /**
     * Sets the value of the authorImageURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorImageURL(String value) {
        this.authorImageURL = value;
    }

    /**
     * Gets the value of the rating property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRating(BigDecimal value) {
        this.rating = value;
    }

    /**
     * Gets the value of the ratingNumber property.
     * 
     */
    public int getRatingNumber() {
        return ratingNumber;
    }

    /**
     * Sets the value of the ratingNumber property.
     * 
     */
    public void setRatingNumber(int value) {
        this.ratingNumber = value;
    }

    /**
     * Gets the value of the cost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets the value of the cost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCost(BigDecimal value) {
        this.cost = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link int }
     *     
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
     *     
     */
    public void setDuration(int value) {
        this.duration = value;
    }

    /**
     * Gets the value of the syllabus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSyllabus() {
        return syllabus;
    }

    /**
     * Sets the value of the syllabus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSyllabus(String value) {
        this.syllabus = value;
    }

    /**
     * Gets the value of the previewVideoURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreviewVideoURL() {
        return previewVideoURL;
    }

    /**
     * Sets the value of the previewVideoURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreviewVideoURL(String value) {
        this.previewVideoURL = value;
    }

    /**
     * Gets the value of the imageURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Sets the value of the imageURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageURL(String value) {
        this.imageURL = value;
    }

    /**
     * Gets the value of the sourceURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceURL() {
        return sourceURL;
    }

    /**
     * Sets the value of the sourceURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceURL(String value) {
        this.sourceURL = value;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", domainId=" + domainId +
                ", categoryId=" + categoryId +
                ", authorImageURL='" + authorImageURL + '\'' +
                ", rating=" + rating +
                ", ratingNumber=" + ratingNumber +
                ", cost=" + cost +
                ", duration=" + duration +
                ", previewVideoURL='" + previewVideoURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", sourceURL='" + sourceURL + '\'' +
                '}';
    }
}
