package com.kakaw.peepshow.manager;


/**
 * Created by keetaekhong on 10/14/14.
 */
public class BaseManager {

//    private ObjectGraph mManagerObjectGraph;

    public BaseManager() {
//        mManagerObjectGraph = PeepShowApplication.getApplication().getApplicationGraph().plus(getModules().toArray());
//
//        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
//        mManagerObjectGraph.inject(this);
//        Log.i("KEETAEK", "injecting graph");

    }

    /**
     * A list of modules to use for the individual activity graph. Subclasses can override this
     * method to provide additional modules provided they call and include the modules returned by
     * calling {@code super.getModules()}.
     */
//    protected List<Object> getModules() {
//        return Arrays.<Object>asList(new ManagerModule(this));
//    }

}
