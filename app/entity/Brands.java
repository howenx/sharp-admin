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
		private Long brandId;

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

		public Brands(){}

		public Brands(Long brandId, String descriptionCn, String descriptionEn, String descriptionKo, String logoUrl, String homePage, Timestamp createDate, String brandNmCn, String brandNmEn, String brandNmKr) {
			this.brandId = brandId;
			this.descriptionCn = descriptionCn;
			this.descriptionEn = descriptionEn;
			this.descriptionKo = descriptionKo;
			this.logoUrl = logoUrl;
			this.homePage = homePage;
			this.createDate = createDate;
			this.brandNmCn = brandNmCn;
			this.brandNmEn = brandNmEn;
			this.brandNmKr = brandNmKr;
		}

		@Override
		public String toString() {
			return "Brands{" +
					"brandId=" + brandId +
					", descriptionCn='" + descriptionCn + '\'' +
					", descriptionEn='" + descriptionEn + '\'' +
					", descriptionKo='" + descriptionKo + '\'' +
					", logoUrl='" + logoUrl + '\'' +
					", homePage='" + homePage + '\'' +
					", createDate=" + createDate +
					", brandNmCn='" + brandNmCn + '\'' +
					", brandNmEn='" + brandNmEn + '\'' +
					", brandNmKr='" + brandNmKr + '\'' +
					'}';
		}

		public Long getBrandId() {
			return brandId;
		}

		public void setBrandId(Long brandId) {
			this.brandId = brandId;
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
