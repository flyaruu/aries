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
package org.apache.aries.jpa.example.tasklist.blueprint.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.aries.jpa.example.tasklist.blueprint.impl.TaskServiceImpl;
import org.apache.aries.jpa.example.tasklist.model.Task;
import org.apache.aries.jpa.example.tasklist.model.TaskService;
import org.junit.Assert;
import org.junit.Test;

public class TaskServiceImplTest {
    @Test
    public void testPersistence() {
        TaskServiceImpl taskServiceImpl = new TaskServiceImpl();
        EntityManagerFactory emf = createTestEMF();
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        taskServiceImpl.em = em;

        TaskService taskService = taskServiceImpl;

        Task task = new Task();
        task.setId(1);
        task.setTitle("test");
        taskService.addTask(task);

        Task task2 = taskService.getTask(1);
        Assert.assertEquals(task.getTitle(), task2.getTitle());
        em.getTransaction().commit();
        em.close();
    }

    private EntityManagerFactory createTestEMF() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        properties.put("javax.persistence.jdbc.url", "jdbc:derby:memory:TEST;create=true");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tasklist", properties);
        return emf;
    }
}
