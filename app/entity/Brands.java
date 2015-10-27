package entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A entity for commodity brands.
 *
 * <p>
 *
 * @author Sunny Wu
 */
public class Brands implements Serializable {

		/**
		 * brand id.
		 */
		private Integer brandId;

		/**
		 * category id.
		 */
		private Integer cateId;

		/**
		 * brand chinese description.
		 */
		private String descriptionCn;

		/**
		 * brand english description.
		 */
		private String descriptionEn;

		/**
		 * brand korean description.
		 */
		private String descriptionKo;

		/**
		 * logo url.
		 */
		private String logoUrl;

		/**
		 * brand internet homepage.
		 */
		private String homePage;

		/**
		 * brand create date.
		 */
		private Timestamp createDate;

		/**
		 * brand chinese name.
		 */
		private String brandNmCn;

		/**
		 * brand english name.
		 */
		private String brandNmEn;

		/**
		 * brand korean name.
		 */
		private String brandNmKr;

		public Integer getBrandId() {
			return brandId;
		}

		public void setBrandId(Integer brandId) {
			this.brandId = brandId;
		}

		public Integer getCateId() {
			return cateId;
		}

		public void setCateId(Integer cateId) {
			this.cateId = cateId;
		}

		public String getDescriptionCn() {
			return descriptionCn;
		}

		public void setDescriptionCn(String descriptionCn) {
			this.descriptionCn = descriptionCn;
		}

		public String getDescriptionEn() {
			return descriptionEn;
		}

		public void setDescriptionEn(String descriptionEn) {
			this.descriptionEn = descriptionEn;
		}

		public String getDescriptionKo() {
			return descriptionKo;
		}

		public void setDescriptionKo(String descriptionKo) {
			this.descriptionKo = descriptionKo;
		}

		public String getLogoUrl() {
			return logoUrl;
		}

		public void setLogoUrl(String logoUrl) {
			this.logoUrl = logoUrl;
		}

		public String getHomePage() {
			return homePage;
		}

		public void setHomePage(String homePage) {
			this.homePage = homePage;
		}

		public Timestamp getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Timestamp createDate) {
			this.createDate = createDate;
		}

		public String getBrandNmCn() {
			return brandNmCn;
		}

		public void setBrandNmCn(String brandNmCn) {
			this.brandNmCn = brandNmCn;
		}

		public String getBrandNmEn() {
			return brandNmEn;
		}

		public void setBrandNmEn(String brandNmEn) {
			this.brandNmEn = brandNmEn;
		}

		public String getBrandNmKr() {
			return brandNmKr;
		}

		public void setBrandNmKr(String brandNmKr) {
			this.brandNmKr = brandNmKr;
		}
}
