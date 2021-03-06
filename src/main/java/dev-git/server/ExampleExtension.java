package dev-git.server;

import dev-git.config.ExampleConfiguration;
import dev-git.dao.IExampleExtensionDAO;
import dev-git.dao.IMoreInterestingExampleExtensionDAO;
import dev-git.dao.impl.ExampleExtensionDAOImpl;
import dev-git.dao.impl.MoreInterestingExampleExtensionDAOImpl;
import dev-git.jpa.impl.ExampleEntity;
import dev-git.jpa.impl.MoreInterestingExampleEntity;
import io.dropwizard.setup.Bootstrap;
import net.smartcosmos.platform.api.IContext;
import net.smartcosmos.platform.base.AbstractServerExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExampleExtension
        extends AbstractServerExtension<ExampleConfiguration>
{

    private static final Logger LOG = LoggerFactory.getLogger(ExampleExtension.class);

    // NOTE TO EXTENSION DEVELOPER:
    // The EXTENSION_ID has to be unique. Don't use this string!
    // Generate your own with:
    // java.util.UUID.getRandomUUID().toString()
    // and replace this string with that.
    //

    public static final String EXTENSION_ID = "d63e47a0-2899-4c4e-bb43-12fb87363405";

    public ExampleExtension()

    {
        super(EXTENSION_ID, "ExampleExtension", ExampleConfiguration.class);
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap)
    {
        super.initialize(bootstrap);
    }


    // NOTE TO EXTENSION DEVELOPER:
    // This method is your only access to the application context. If you need the context for other methods
    // here, set a local net.smartcosmos.platform.api.IContext context variable to the argument of this method.
    //
    // NOTE TO EXTENSION DEVELOPER:
    // If you have your own Data Access Object, register them here with the DAO Factory. Note that DAOs registered
    // at startup can only be found with the method:
    //
    // context.getDAOFactory().lookup(IExampleExtensionDAO.class.getName(), IExampleExtensionDAO.class);
    // (see e.g., ExampleExtensionPutRequestHandler.java for an example)
    //
    // unlike core Objects DAOs, which can be retrieved like (e.g.,) :
    //
    // context.getDAOFactory().getMetadataDAO()

    @Override
    public void registerResources(IContext context)
    {

        IExampleExtensionDAO exampleExtensionDAO =
                new ExampleExtensionDAOImpl(context.getSessionFactory());

        context.getDAOFactory().register(IExampleExtensionDAO.class.getName(),
                                         exampleExtensionDAO,
                                         "ExampleExtensionDAO");

        IMoreInterestingExampleExtensionDAO moreInterestingExampleExtensionDAO =
                new MoreInterestingExampleExtensionDAOImpl(context.getSessionFactory());

        context.getDAOFactory().register(IMoreInterestingExampleExtensionDAO.class.getName(),
                                         moreInterestingExampleExtensionDAO,
                                         "MoreInterestingExampleExtensionDAO");

    }

    // NOTE TO EXTENSION DEVELOPER:
    // The method that DropWizard actually uses to register entities is the getEntities() method below.
    // We are, and you should too, creating a static List of entities at the time of class instantiation
    // and returning it from the getEntities() method. This ensures the availablility of these entities
    // in testing.
    public static List<Class<?>> entities()
    {
        //
        // Entities are returned irrespective of the bundle enablement flag
        //
        List<Class<?>> exampleEntitiesList = new ArrayList<>();

        exampleEntitiesList.add(ExampleEntity.class);
        exampleEntitiesList.add(MoreInterestingExampleEntity.class);

        return exampleEntitiesList;
    }

    @Override
    public List<Class<?>> getEntities()
    {
        return entities();
    }
}
