package vn.triplet.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import vn.triplet.dao.ProductDAO;
import vn.triplet.helper.Converter;
import vn.triplet.model.Product;
import vn.triplet.service.ProductService;

public class ProductServiceImpl extends BaseServiceImpl implements ProductService {

	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public Product findById(Serializable key) {
		try {
			Product product = productDAO.findById(key);
			product = Converter.parseInformationOfProduct(product);
			return product;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Product saveOrUpdate(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Product entity) {
		try {
			entity.setDelete_time(new Date());
			getProductDAO().saveOrUpdate(entity);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Product> loadHotTrendProduct(int gender) {
		try {
			List<Product> products = productDAO.loadHotTrendProduct(gender);
			products = Converter.parseInformationOfProduct(products);
			return products;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public List<Product> loadProductWithCategoryId(int categoryId, int productId) {
		try {
			List<Product> products = productDAO.loadProductWithCategoryId(categoryId, productId);
			products = Converter.parseInformationOfProduct(products);
			return products;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public List<Product> loadProductWithListProductId(List<Integer> ids) {
		try {
			List<Product> products = productDAO.loadProductWithListProductId(ids);
			products = Converter.parseInformationOfProduct(products);
			return products;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public List<Product> loadFullProducts() {

		return Converter.parseInformationOfProduct(getProductDAO().loadFullProducts());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> loadProductWithFilter(int categoryId, String productName, Integer fromprice, Integer toprice,
			Integer rating, Integer page) {
		try {
			List<Object> objects = productDAO.loadProductWithFilter(categoryId, productName, fromprice, toprice, rating,
					page);
			objects.set(0, Converter.parseInformationOfProduct((List<Product>) objects.get(0)));
			return objects;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

}
