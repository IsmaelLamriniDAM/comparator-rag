package epp.tgf.app.service.brand;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataBrandServiceImpTest {

	@Test
	@DisplayName("Confirm that this method return all Brands in DB.")
	void testGetAllBrands() {
		fail("Not yet implemented");
	}
	
	@Test
	@DisplayName("Verify that Brands has been persistenced in DB.")
	void testSaveAllBrands() {fail("Not yet implemented");}
	
	@Test
	@DisplayName("Check that got brands by name in DB.")
	void testGetBrandsByName() {fail("Not yet implemented");}
	
	@Test
	@DisplayName("Check that got brands and his models in DB.")
	void testGetAllBrandsAndHisModels() {fail("Not yet implemented");}
	
	@Test
	@DisplayName("Check that got brands by one name.")
	void testGetBrandForThisName() {fail("Not yet implemented");}
}
