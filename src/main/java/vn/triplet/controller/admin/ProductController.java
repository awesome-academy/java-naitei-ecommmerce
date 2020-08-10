package vn.triplet.controller.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.model.Product;
import vn.triplet.service.CategoryService;
import vn.triplet.service.ProductService;

@PropertySource("classpath:messages.properties")
@Controller(value = "productControllerOfadmin")
@RequestMapping("/admin/products")
public class ProductController {
	private static final Logger logger = Logger.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Value("${messages.error}")
	private String typeCss_error = "error";

	@Value("${messages.success}")
	private String typeCss_success = "success";

	@Value("${messages.notFound}")
	private String message_product_not_found;

	@Value("${messages.create.product.fail}")
	private String messages_create_product_fail;

	@Value("${messages.create.product.success}")
	private String messages_create_product_success;

	@Value("${messages.delete.product.fail}")
	private String messages_delete_product_fail;

	@Value("${messages.delete.product.success}")
	private String messages_delete_product_success;

	@GetMapping(value = { "", "/" })
	public String index(Model model) {
		logger.info("show list product");
		List<Product> products = productService.loadFullProducts();
		model.addAttribute("products", products);
		return "views/admin/product/products";
	}

	@GetMapping(value = "/{id}")
	public String showProduct(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("show a product");
		Product product = productService.findById(id);
		if (product == null) {
			redirectAttributes.addFlashAttribute("css", typeCss_error);
			redirectAttributes.addFlashAttribute("msg", message_product_not_found);
			return "views/admin/product/products";
		}
		model.addAttribute("product", product);
		return "views/admin/product/product";
	}

	@GetMapping(value = "/{id}/edit")
	public String updateProduct(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		Product product = productService.findById(id);
		if (product == null) {
			redirectAttributes.addFlashAttribute("css", typeCss_error);
			redirectAttributes.addFlashAttribute("msg", message_product_not_found);
			return "redirect:/admin/products";
		}
		model.addAttribute("categories", categoryService.loadFullCategories());
		model.addAttribute("productForm", product);
		model.addAttribute("status", "update");
		return "views/admin/product/product-form";

	}

	@GetMapping(value = "/add")
	public String createProduct(Model model) {
		model.addAttribute("newProduct", new Product());
		model.addAttribute("status", "add");
		return "views/admin/product/product-form";
	}

	@RequestMapping(value = "/saveUpdate")
	public String saveOrUpdate(@ModelAttribute("productForm") Product product, Model model,
			final RedirectAttributes redirectAttributes) {

		if (productService.saveOrUpdate(product) == null) {
			redirectAttributes.addFlashAttribute("css", typeCss_error);
			redirectAttributes.addFlashAttribute("msg", messages_create_product_fail);

		}
		redirectAttributes.addFlashAttribute("css", typeCss_success);
		redirectAttributes.addFlashAttribute("msg", messages_create_product_success);
		return "redirect:/admin/products";

	}

	@GetMapping(value = "/{id}/delete")
	public String deleteProduct(@PathVariable("id") Integer id, final RedirectAttributes redirectAttributes) {
		Product product = productService.findById(id);
		if (product == null) {
			redirectAttributes.addFlashAttribute("css", typeCss_error);
			redirectAttributes.addFlashAttribute("msg", message_product_not_found);
		} else if (productService.delete(product)) {
			redirectAttributes.addFlashAttribute("css", typeCss_success);
			redirectAttributes.addFlashAttribute("msg", messages_delete_product_success);
		}else
		{
			redirectAttributes.addFlashAttribute("css", typeCss_error);
			redirectAttributes.addFlashAttribute("msg", messages_delete_product_success);
		}
		return "redirect:/admin/products";
	}
}
