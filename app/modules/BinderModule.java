package modules;

import com.google.inject.AbstractModule;
import service.ItemService;
import service.ItemServiceImpl;
import service.ThemeService;
import service.ThemeServiceImpl;

/**
 * Guice bind interface to its implements and deposit to guice.
 * <p>
 * <p>
 *
 * @author Howen Xiong
 */
public class BinderModule extends AbstractModule {

    /**
     * bing configure.
     */
    protected void configure() {
        bind(ItemService.class).to(ItemServiceImpl.class);
        bind(ThemeService.class).to(ThemeServiceImpl.class);
    }
}