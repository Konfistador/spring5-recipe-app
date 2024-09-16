package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import guru.springframework.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    IndexController sut;

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    @Before
    public void setUp( ) throws Exception {
        MockitoAnnotations.initMocks( this );
        sut = new IndexController( recipeService );
    }

    /**
     * Great example on how to test Controllers.
     * @throws Exception but it doesn't :)
     */

    @Test
    public void testMockMVC( ) throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup( sut ).build();

        mockMvc.perform( get("/") )
                .andExpect( status().isOk() )
                .andExpect( view().name( "index" ));
    }

    @Test
    public void getIndexPage( ) {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass( String.class );
        ArgumentCaptor<Set<Recipe>> recipeSetCaptor = ArgumentCaptor.forClass( Set.class );

        Set<Recipe> recipeSet = mock(Set.class);
        when(recipeService.getRecipes()).thenReturn( recipeSet );

        String viewName = sut.getIndexPage(model);

        assertEquals( "index", viewName );

        verify(model,times( 1 )).addAttribute( stringCaptor.capture(), recipeSetCaptor.capture() );

        String capturedStringKey = stringCaptor.getValue();
        Set<Recipe> capturedSet = recipeSetCaptor.getValue();

        assertEquals("recipes", capturedStringKey);
        assertEquals( recipeSet, capturedSet );
    }
}