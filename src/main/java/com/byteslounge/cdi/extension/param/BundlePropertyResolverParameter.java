/*
 * Copyright 2014 byteslounge.com (Gonçalo Marques).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.byteslounge.cdi.extension.param;

import javax.enterprise.context.spi.CreationalContext;

import com.byteslounge.cdi.resolver.context.ResolverContext;

/**
 * Represents a resolver that will be used to evaluate a 
 * parameter of the property resolver method of type Bundle
 * 
 * @author Gonçalo Marques
 * @since 1.1.0
 */
public class BundlePropertyResolverParameter implements ResolverParameter<String> {

    /**
     * see {@link ResolverParameter#resolve(String, String, CreationalContext)}
     */
    @Override
    public <T> String resolve(ResolverContext resolverContext, CreationalContext<T> ctx) {
        return resolverContext.getBundleName();
    }

}
