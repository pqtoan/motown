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

import io.motown.chargingstationconfiguration.viewmodel.persistence.entities.ChargingStationType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ChargingStationTypeRepository {

    private EntityManagerFactory entityManagerFactory;

    public List<ChargingStationType> findByCodeAndManufacturerCode(String code, String manufacturerCode) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT cst FROM ChargingStationType AS cst where UPPER(cst.code) = UPPER(:code) and UPPER(cst.manufacturer.code) = UPPER(:manufacturerCode)", ChargingStationType.class)
                .setParameter("code", code)
                .setParameter("manufacturerCode", manufacturerCode)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public ChargingStationType createOrUpdate(final ChargingStationType chargingStationType) {
        EntityManager em = getEntityManager();

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            ChargingStationType persistedChargingStationType = em.merge(chargingStationType);

            tx.commit();

            return persistedChargingStationType;
        } catch (Exception e) {
            if(tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<ChargingStationType> findAll(int offset, int limit) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT cst FROM ChargingStationType cst", ChargingStationType.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public Long getTotalNumberOfChargingStationTypes() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(cst) FROM ChargingStationType cst", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public ChargingStationType findOne(Long id) {
        EntityManager em = getEntityManager();
        try {
            return findOne(id, em);
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = getEntityManager();

        ChargingStationType chargingStationType = findOne(id, em);

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            em.remove(chargingStationType);

            tx.commit();
        } catch (Exception e) {
            if(tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    private ChargingStationType findOne(Long id, EntityManager entityManager) {
        ChargingStationType chargingStationType = entityManager.find(ChargingStationType.class, id);
        if (chargingStationType != null) {
            return chargingStationType;
        }
        throw new EntityNotFoundException(String.format("Unable to find charging station type with id '%s'", id));
    }

}
