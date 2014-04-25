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
import io.motown.chargingstationconfiguration.viewmodel.persistence.entities.Evse;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ChargingStationTypeRepository {

    private EntityManager entityManager;

    public List<ChargingStationType> findByCodeAndManufacturerCode(String code, String manufacturerCode) {
        return entityManager.createQuery("SELECT cst FROM ChargingStationType AS cst where UPPER(cst.code) = UPPER(:code) and UPPER(cst.manufacturer.code) = UPPER(:manufacturerCode)", ChargingStationType.class)
                .setParameter("code", code)
                .setParameter("manufacturerCode", manufacturerCode)
                .getResultList();
    }

    public ChargingStationType findByEvseId(Long evseId) {
        return entityManager.createQuery("SELECT cst FROM ChargingStationType AS cst WHERE :evse MEMBER OF cst.evses", ChargingStationType.class).setParameter("evse", evseId).getSingleResult();
    }

    public ChargingStationType findByConnectorId(Long connectorId) {
        Evse evse = entityManager.createQuery("SELECT evse FROM Evse AS evse WHERE :connector MEMBER OF evse.connectors", Evse.class).setParameter("connector", connectorId).getSingleResult();
        return findByEvseId(evse.getId());
    }

    public ChargingStationType createOrUpdate(ChargingStationType chargingStationType) {
        EntityTransaction transaction = entityManager.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }

        try {
            ChargingStationType persistentChargingStationType = entityManager.merge(chargingStationType);
            transaction.commit();
            return persistentChargingStationType;
        } catch (Exception e) {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<ChargingStationType> findAll() {
        return entityManager.createQuery("SELECT cst FROM ChargingStationType cst", ChargingStationType.class).getResultList();
    }

    public ChargingStationType findOne(Long id) {
        ChargingStationType chargingStationType = entityManager.find(ChargingStationType.class, id);
        if (chargingStationType != null) {
            return chargingStationType;
        }
        throw new EntityNotFoundException(String.format("Unable to find charging station type with id '%s'", id));
    }

    public void delete(Long id) {
        ChargingStationType chargingStationType = findOne(id);
        EntityTransaction transaction = entityManager.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }

        try {
            entityManager.remove(chargingStationType);
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
