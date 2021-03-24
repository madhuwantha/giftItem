import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IGiftItem } from 'app/shared/model/gift-item.model';
import { getEntities as getGiftItems } from 'app/entities/gift-item/gift-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './gift-order.reducer';
import { IGiftOrder } from 'app/shared/model/gift-order.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGiftOrderUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GiftOrderUpdate = (props: IGiftOrderUpdateProps) => {
  const [idsgiftItems, setIdsgiftItems] = useState([]);
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { giftOrderEntity, users, giftItems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/gift-order');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getGiftItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...giftOrderEntity,
        ...values,
        giftItems: mapIdList(values.giftItems),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="giftItemApp.giftOrder.home.createOrEditLabel">Create or edit a GiftOrder</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : giftOrderEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="gift-order-id">ID</Label>
                  <AvInput id="gift-order-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descripptionLabel" for="gift-order-descripption">
                  Descripption
                </Label>
                <AvField id="gift-order-descripption" type="text" name="descripption" />
              </AvGroup>
              <AvGroup>
                <Label for="gift-order-user">User</Label>
                <AvInput id="gift-order-user" type="select" className="form-control" name="user.id">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="gift-order-giftItems">Gift Items</Label>
                <AvInput
                  id="gift-order-giftItems"
                  type="select"
                  multiple
                  className="form-control"
                  name="giftItems"
                  value={giftOrderEntity.giftItems && giftOrderEntity.giftItems.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {giftItems
                    ? giftItems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/gift-order" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  giftItems: storeState.giftItem.entities,
  giftOrderEntity: storeState.giftOrder.entity,
  loading: storeState.giftOrder.loading,
  updating: storeState.giftOrder.updating,
  updateSuccess: storeState.giftOrder.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getGiftItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GiftOrderUpdate);
