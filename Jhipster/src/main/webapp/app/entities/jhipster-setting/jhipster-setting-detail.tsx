import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './jhipster-setting.reducer';

export const JhipsterSettingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jhipsterSettingEntity = useAppSelector(state => state.jhipsterSetting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jhipsterSettingDetailsHeading">
          <Translate contentKey="jhipsterApp.jhipsterSetting.detail.title">JhipsterSetting</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jhipsterSettingEntity.id}</dd>
          <dt>
            <span id="theme">
              <Translate contentKey="jhipsterApp.jhipsterSetting.theme">Theme</Translate>
            </span>
          </dt>
          <dd>{jhipsterSettingEntity.theme}</dd>
          <dt>
            <span id="pageSize">
              <Translate contentKey="jhipsterApp.jhipsterSetting.pageSize">Page Size</Translate>
            </span>
          </dt>
          <dd>{jhipsterSettingEntity.pageSize}</dd>
          <dt>
            <span id="others">
              <Translate contentKey="jhipsterApp.jhipsterSetting.others">Others</Translate>
            </span>
          </dt>
          <dd>{jhipsterSettingEntity.others}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.jhipsterSetting.jhipster">Jhipster</Translate>
          </dt>
          <dd>{jhipsterSettingEntity.jhipster ? jhipsterSettingEntity.jhipster.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/jhipster-setting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/jhipster-setting/${jhipsterSettingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JhipsterSettingDetail;
