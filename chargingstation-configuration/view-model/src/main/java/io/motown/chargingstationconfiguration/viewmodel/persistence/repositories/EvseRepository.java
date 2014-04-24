/**
 * Copyright (C) 2013 Motown.IO (info@motown.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.motown.chargingstationconfiguration.viewmodel.persistence.repositories;

import io.motown.chargingstationconfiguration.viewmodel.persistence.entities.Evse;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

public class EvseRepository {

    private EntityManager entityManager;

    public Evse createOrUpdate(Evse evse) {
        EntityTransaction transaction = entityManager.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }

        try {
            Evse persistentEvse = entityManager.merge(evse);
            transaction.commit();
            return persistentEvse;
        } catch (Exception e) {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Evse findOne(Long id) {
        Evse evse = entityManager.find(Evse.class, id);
        if (evse != null) {
            return evse;
        }
        throw new EntityNotFoundException(String.format("Unable to find evse with id '%s'", id));
    }

    public void delete(Long id) {
        Evse evse = findOne(id);
        EntityTransaction transaction = entityManager.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }

        try {
            entityManager.remove(evse);
            transaction.commit();
        } catch (Exception e) {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
