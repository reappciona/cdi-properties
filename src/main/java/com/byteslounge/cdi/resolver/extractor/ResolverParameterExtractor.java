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
package com.byteslounge.cdi.resolver.extractor;

import javax.enterprise.inject.spi.AnnotatedParameter;

import com.byteslounge.cdi.extension.param.ResolverParameter;

/**
 * Determines the strategy used to fetch the value of a given property resolver method parameter
 * 
 * @author Gonçalo Marques
 * @since 1.1.0
 */
public interface ResolverParameterExtractor<T extends ResolverParameter<?>> {

    /**
     * Returns the resolver that will be used to extract a given property resolver method parameter
     * 
     * @param parameter
     *            The parameter type being checked
     * @return the resolver for the given parameter
     */
    T extract(AnnotatedParameter<?> parameter);

}
