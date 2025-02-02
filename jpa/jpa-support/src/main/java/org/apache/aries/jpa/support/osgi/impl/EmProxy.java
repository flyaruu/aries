/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIESOR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.aries.jpa.support.osgi.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EntityManager;

import org.apache.aries.jpa.supplier.EmSupplier;

public class EmProxy implements InvocationHandler {
    EmSupplier emSupplier;

    public EmProxy(EmSupplier emSupplier) {
        this.emSupplier = emSupplier;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        EntityManager em = emSupplier.get();
        if (em == null) {
            throw new IllegalStateException("EntityManager not available. Make sure you run in an @Transactional method");
        }
        try {
            return method.invoke(em, args);
        } catch (InvocationTargetException ex) {
            InvocationTargetException iex = (InvocationTargetException)ex;
            throw iex.getTargetException();
        }
    }

}
