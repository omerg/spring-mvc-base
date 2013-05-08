package com.obss.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.obss.model.GedikStock;
import com.obss.model.response.ResponseGedikStockList;
import com.obss.util.JerryUtils;

/**
 * 
 * Main service associated with operations on gedikStocks
 * 
 */
@Service("gedikStockService")
public class GedikStockService extends StockService<GedikStock> {

	/**
	 * 
	 * Folder to store CSV files
	 * 
	 */
	public static final String TMP_PATH = "tmp";

	public static final String STOCK_LIST_FILE_NAME = "stockList.csv";

	public static final String GEDIK_YATIRIM_MAIN_URL = "http://www.gedik.com/hisse/tum_hisse_fiyatlari.aspx";

	public static int NR_OF_IMKB_STOCKS = 759;

	public static final String HISSE_TABLE_CSS_CLASS = ".HisseTable";

	private static final Integer COLUMN_INDEX_STOCK_NAME = 1;
	private static final Integer COLUMN_INDEX_STOCK_PRICE = 3;
	private static final Integer COLUMN_INDEX_PRICE_DIFF = 5;
	private static final Integer COLUMN_INDEX_VOLUME = 7;
	private static final Integer COLUMN_INDEX_HIGHEST_PRICE = 9;
	private static final Integer COLUMN_INDEX_LOWEST_PRICE = 11;

	protected static Logger logger = Logger.getLogger("sessionListener");

	/**
	 * 
	 * retrieve all account data
	 * 
	 * @return all account's list
	 * @throws IOException 
	 */
	public ResponseGedikStockList getAllStocks() throws IOException {

		final ResponseGedikStockList response = new ResponseGedikStockList();
		final List<GedikStock> stockList = new ArrayList<GedikStock>();

		logger.debug("Reading " + GEDIK_YATIRIM_MAIN_URL + "...");
		Jerry doc = JerryUtils.getJerry(GEDIK_YATIRIM_MAIN_URL);

		logger.debug("Reading complete!");

		doc.$(HISSE_TABLE_CSS_CLASS + " tr").each(new JerryFunction() {
			
			public boolean onNode(Jerry $this, int index) {

				// skip header
				if ($this.parent().is("thead")) {
					return true;
				}

				GedikStock stock = new GedikStock();

				String stockName = $this.get(0)
						.getChild(COLUMN_INDEX_STOCK_NAME).getTextContent()
						.trim();
				Float stockPrice = Float.valueOf($this.get(0)
						.getChild(COLUMN_INDEX_STOCK_PRICE).getTextContent()
						.trim().replace(".", "").replace(",", "."));
				Float priceDiff = Float.valueOf($this.get(0)
						.getChild(COLUMN_INDEX_PRICE_DIFF).getTextContent()
						.trim().replace(",", "."));
				Float volume = Float.valueOf($this.get(0)
						.getChild(COLUMN_INDEX_VOLUME).getTextContent().trim()
						.replace(".", "").replace(",", "."));
				Float highestPrice = Float.valueOf($this.get(0)
						.getChild(COLUMN_INDEX_HIGHEST_PRICE).getTextContent()
						.trim().replace(".", "").replace(",", "."));
				Float lowestPrice = Float.valueOf($this.get(0)
						.getChild(COLUMN_INDEX_LOWEST_PRICE).getTextContent()
						.trim().replace(".", "").replace(",", "."));

				stock.setStockName(stockName);
				stock.setStockPrice(stockPrice);
				stock.setPriceDiff(priceDiff);
				stock.setVolume(volume);
				stock.setHighestPrice(highestPrice);
				stock.setLowestPrice(lowestPrice);

				logger.debug(stock.toString());

				stockList.add(stock);
				response.setGedikStockList(stockList);
				return true;
			};
		});
		return response;
	}
}
