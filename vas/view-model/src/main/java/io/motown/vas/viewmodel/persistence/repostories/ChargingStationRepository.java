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
package io.motown.vas.viewmodel.persistence.repostories;

import io.motown.vas.viewmodel.persistence.entities.ChargingStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ChargingStationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ChargingStationRepository.class);

    private EntityManagerFactory entityManagerFactory;

    public ChargingStation createOrUpdate(ChargingStation chargingStation) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            ChargingStation persistedChargingStation = entityManager.merge(chargingStation);
            transaction.commit();

            return persistedChargingStation;
        } finally {
            if (transaction != null && transaction.isActive()) {
                LOG.warn("Transaction is still active while it should not be, rolling back.");
                transaction.rollback();
            }
            entityManager.close();
        }
    }

    public ChargingStation findOne(String id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(ChargingStation.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<ChargingStation> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT cs FROM io.motown.vas.viewmodel.persistence.entities.ChargingStation AS cs", ChargingStation.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }
}
