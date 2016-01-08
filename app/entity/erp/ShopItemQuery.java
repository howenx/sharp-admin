package entity.erp;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.B1EC2Client;
import com.iwilley.b1ec2.api.domain.ShopItem;
import com.iwilley.b1ec2.api.domain.ShopSku;
import com.iwilley.b1ec2.api.request.ShopItemQueryRequest;
import com.iwilley.b1ec2.api.response.ShopItemQueryResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Sunny Wu on 16/1/6.
 * ERP ÉÌÆ·²éÑ¯
 */
public class ShopItemQuery {

	public static void main(String[] args) throws ApiException, ParseException {
		B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY,
				Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);

		int pageSize = 10;
		DateFormat format = new SimpleDateFormat(
				com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);

		ShopItemQueryRequest request = new ShopItemQueryRequest();
		request.setStartTime(format.parse("2015-07-20 00:00:00"));
		request.setEndTime(format.parse("2016-01-07 00:00:00"));
		request.setPageSize(pageSize);

		ShopItemQueryResponse response = client.execute(request);
		System.out.println(response.getBody());
		System.out.println("Results:" + response.getTotalResults());

		if (response.getErrorCode() == 0 && response.getTotalResults() > 0) {
			int totalPages = (int) Math.ceil((double) response
					.getTotalResults() / pageSize);

			for (int i = 1; i <= totalPages && i <= 5; i++) {
				request.setPageNum(i);
				response = client.execute(request);
				System.out.println("page:" + i + "/" + totalPages);

				for (ShopItem shopItem : response.getShopItems()) {
					System.out.println("Item Info:" + shopItem.getShopItemCode() + ","
							+ shopItem.getShopItemName());

					for (ShopSku shopSku : shopItem.getLines()) {
						System.out.println("  SKU Info:" + shopSku.getShopSkuCode()
								+ "," + shopSku.getProperties());
					}
				}
				System.out.println();
			}
		}
	}

}