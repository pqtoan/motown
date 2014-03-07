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
package io.motown.ocpp.viewmodel.persistence.repostories;

import io.motown.ocpp.viewmodel.persistence.entities.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransactionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationIdentifierRepository.class);

    private EntityManager entityManager;

    public Transaction findTransactionById(Long id) {
        return entityManager.find(Transaction.class, id);
    }

    public void insert(Transaction transaction) {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {
            entityManager.persist(transaction);
            entityTransaction.commit();
        } finally {
            if (entityTransaction.isActive()) {
                LOG.warn("Transaction is still active while it should not be, rolling back.");
                entityTransaction.rollback();
            }
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}