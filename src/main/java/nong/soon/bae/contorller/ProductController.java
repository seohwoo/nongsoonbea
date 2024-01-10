package nong.soon.bae.contorller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import nong.soon.bae.bean.AllProductDTO;
import nong.soon.bae.bean.ProductCategoryDTO;
import nong.soon.bae.bean.ProductDTO;
import nong.soon.bae.bean.UsersDTO;
import nong.soon.bae.service.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService service;

	// 일단 상점 메인
	@RequestMapping("productMain")
	public String productMain(Model model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("username", username);
		return "product/productMain";
	}	
	
	// 개인 상점(테이블) 만들기
	@RequestMapping("createProduct")
	public String createProduct(Model model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("username", username);
		return "product/createProduct";
	}
	
	// 개인 상점, 이미지 테이블 만들기
	@RequestMapping("createProductPro")
	public String createProductPro(Model model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("username", username);
		
		service.createProduct(username);
		service.createImages(username);
		return "redirect:/product/productMain";
	}
	

	
	// FINISH
	
	// 상품 등록
	@RequestMapping("productWriteForm")
	public String productWriteForm(HttpServletRequest request, Model model, Principal principal, String cate4, @RequestParam(value="productnum", defaultValue="0")String productnum) {
		String username = principal.getName();
		model.addAttribute("username", username);
		model.addAttribute("productnum", productnum);
		return "product/productWriteForm";
	}
	
	@RequestMapping("productWritePro")
	public String productWritePro(HttpServletRequest request, List<MultipartFile> files, Model model, Principal principal, ProductDTO product, AllProductDTO dto, String cate3, String productnum) {
		String username = principal.getName();
		model.addAttribute("username", username);
		product.setUsername(username);
		product.setProductnum(cate3); 		
		product.setSeqnum("C_"+cate3);	
		
		String path = request.getServletContext().getRealPath("/resources/file/product/");
		int cnt = service.productInsert(product, files, path);
		int result = service.imagesInsert(files, path, username);
		
		int catego1 = cate3.charAt(0)-48;
		int catego2 = cate3.charAt(1)-48;
		int catego3 = cate3.charAt(2)-48;		
	
		dto.setCate1(catego1);
		dto.setCate2(catego2);
		dto.setCate3(catego3);				
	
		if(cnt >0) {
			dto.setProductnum(service.selectProductnum(username));
			service.allproduct(dto);
		}		
		
		productnum = "P_"+service.selectProductnum(username);
		service.createReviews(productnum);
		model.addAttribute("cnt", cnt);
		model.addAttribute("result", result);		
		return "product/productWritePro";
	}
	 
	@RequestMapping("myProduct")
	public String myProduct(Model model, Principal principal, ProductDTO dto) {
		String username = principal.getName();
		model.addAttribute("username", username);
		dto.setUsername(username);
		List<ProductDTO> productDTO = service.myProduct(username);
		model.addAttribute("productDTO", productDTO);
		return "product/myProduct";
	}
	
	
	
	
	
	
	
	////////////////////////

	@PostMapping("sample2")
	public String sample2(@RequestParam("files") List<MultipartFile> files) {
		return "product/sample2";
	}
			
			
	@RequestMapping("sample")
	public String sample() {
		return "product/sample";
	}	
}

