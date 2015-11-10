package modules;

import com.google.inject.AbstractModule;
import service.ProdService;
import service.ProdServiceImpl;
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
        bind(ProdService.class).to(ProdServiceImpl.class);
        bind(ThemeService.class).to(ThemeServiceImpl.class);
    }
}