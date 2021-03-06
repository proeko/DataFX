/**
 * Copyright (c) 2011, 2013, Jonathan Giles, Johan Vos, Hendrik Ebbers
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of DataFX, the website javafxdata.org, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package io.datafx.controller.flow.context;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import io.datafx.controller.context.AbstractContext;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.context.ViewContext;

/**
 * <p>
 * The flow context is the context for a defined flow. A flow is a linked map of
 * different views. By using the context you can easily share your datamodel in
 * all views of a flow.
 * </p>
 * <p>
 * How to use a context<br>
 * A context has a defined life time and can hold the data model of your
 * aplication or of a part of your application. To do so you can easily register
 * objects to your context:<br>
 * </p>
 * <p>
 * DataModel model = ... <br>
 * context.register(model);<br>
 * <br>
 * In your controller: <br>
 * DataModel model = context.getRegisteredObject(DataModel.class);
 * </p>
 * <p>
 * If you need more than one instance of a class in your context you can simple
 * register the by using string based keys:
 * </p>
 * <p>
 * DataModel model1 = ... <br>
 * DataModel model2 = ... <br>
 * context.register("firstModel", model);<br>
 * context.register("secondModel", model);<br>
 * <br>
 * In your controller: <br>
 * DataModel firstModel = context.getRegisteredObject("firstModel");
 * </p>
 * <p>
 * A context can simple used in a controller by injecting the context. DataFX
 * provides annotations to inject all different context types:
 * </p>
 * <p>
 * 
 * "@FXMLApplicationContext" <br>
 * ApplicationContext myApplicationContext;<br>
 * </p>
 * <p>
 * By doing so you can easily access all your data in your controller and share
 * data between different controllers.
 * </p>
 * 
 * @author hendrikebbers
 * 
 */
public class ViewFlowContext extends AbstractContext {

	private ObjectProperty<ViewContext<?>> currentViewContextProperty;

	/**
	 * Default constructor
	 */
	public ViewFlowContext() {
	}

    public ObjectProperty<ViewContext<?>> currentViewContextProperty() {
        if(currentViewContextProperty == null) {
            currentViewContextProperty = new SimpleObjectProperty<ViewContext<?>>() {
                @Override
                protected void invalidated() {
                    super.invalidated();
                    get().register(ViewFlowContext.this);
                }
            };
        }
        return currentViewContextProperty;
    }

    /**
	 * Sets the context of the current view in the flow of this context.
	 * Normally an application will cal this method. The flow API manages all
	 * contexts.
	 * 
	 * @param currentViewContext
	 */
	public <T> void setCurrentViewContext(ViewContext<T> currentViewContext) {
        currentViewContextProperty().setValue(currentViewContext);
	}

	/**
	 * Returns the context of the current view of the flow.
	 * @return the view context
	 */
	public ViewContext<?> getCurrentViewContext() {
		return currentViewContextProperty().get();
	}

	/**
	 * Returns the global application context
	 * 
	 * @return application context
	 */
	public ApplicationContext getApplicationContext() {
		return ApplicationContext.getInstance();
	}
}
