package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import guru.springframework.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void getIndexPage( ) {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass( String.class );
        ArgumentCaptor<Set<Recipe>> recipeSetCaptor = ArgumentCaptor.forClass( Set.class );

        Set<Recipe> recipeSet = mock(Set.class);
        when(recipeService.getRecipes()).thenReturn( recipeSet );

        String viewName = sut.getIndexPage(model);

        assertEquals( "index", viewName );

        verify(model).addAttribute( stringCaptor.capture(), recipeSetCaptor.capture() );

        String capturedStringKey = stringCaptor.getValue();
        Set<Recipe> capturedSet = recipeSetCaptor.getValue();

        assertEquals("recipes", capturedStringKey);
        assertEquals( recipeSet, capturedSet );
    }
}