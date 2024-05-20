import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './jhipster.reducer';

export const JhipsterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jhipsterEntity = useAppSelector(state => state.jhipster.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jhipsterDetailsHeading">
          <Translate contentKey="jhipsterApp.jhipster.detail.title">Jhipster</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jhipsterEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterApp.jhipster.name">Name</Translate>
            </span>
          </dt>
          <dd>{jhipsterEntity.name}</dd>
          <dt>
            <span id="closed">
              <Translate contentKey="jhipsterApp.jhipster.closed">Closed</Translate>
            </span>
          </dt>
          <dd>{jhipsterEntity.closed ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/jhipster" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/jhipster/${jhipsterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JhipsterDetail;
