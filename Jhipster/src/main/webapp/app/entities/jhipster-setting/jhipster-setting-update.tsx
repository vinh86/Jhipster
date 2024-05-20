import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJhipster } from 'app/shared/model/jhipster.model';
import { getEntities as getJhipsters } from 'app/entities/jhipster/jhipster.reducer';
import { IJhipsterSetting } from 'app/shared/model/jhipster-setting.model';
import { getEntity, updateEntity, createEntity, reset } from './jhipster-setting.reducer';

export const JhipsterSettingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jhipsters = useAppSelector(state => state.jhipster.entities);
  const jhipsterSettingEntity = useAppSelector(state => state.jhipsterSetting.entity);
  const loading = useAppSelector(state => state.jhipsterSetting.loading);
  const updating = useAppSelector(state => state.jhipsterSetting.updating);
  const updateSuccess = useAppSelector(state => state.jhipsterSetting.updateSuccess);

  const handleClose = () => {
    navigate('/jhipster-setting');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getJhipsters({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.pageSize !== undefined && typeof values.pageSize !== 'number') {
      values.pageSize = Number(values.pageSize);
    }

    const entity = {
      ...jhipsterSettingEntity,
      ...values,
      jhipster: jhipsters.find(it => it.id.toString() === values.jhipster?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...jhipsterSettingEntity,
          jhipster: jhipsterSettingEntity?.jhipster?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.jhipsterSetting.home.createOrEditLabel" data-cy="JhipsterSettingCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.jhipsterSetting.home.createOrEditLabel">Create or edit a JhipsterSetting</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="jhipster-setting-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.jhipsterSetting.theme')}
                id="jhipster-setting-theme"
                name="theme"
                data-cy="theme"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterApp.jhipsterSetting.pageSize')}
                id="jhipster-setting-pageSize"
                name="pageSize"
                data-cy="pageSize"
                type="text"
              />
              <ValidatedField
                id="jhipster-setting-jhipster"
                name="jhipster"
                data-cy="jhipster"
                label={translate('jhipsterApp.jhipsterSetting.jhipster')}
                type="select"
                required
              >
                <option value="" key="0" />
                {jhipsters
                  ? jhipsters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/jhipster-setting" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default JhipsterSettingUpdate;
