import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './gift-item.reducer';
import { IGiftItem } from 'app/shared/model/gift-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGiftItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GiftItemUpdate = (props: IGiftItemUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { giftItemEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/gift-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...giftItemEntity,
        ...values,
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
          <h2 id="giftItemApp.giftItem.home.createOrEditLabel">Create or edit a GiftItem</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : giftItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="gift-item-id">ID</Label>
                  <AvInput id="gift-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="giftNameLabel" for="gift-item-giftName">
                  Gift Name
                </Label>
                <AvField id="gift-item-giftName" type="text" name="giftName" />
              </AvGroup>
              <AvGroup>
                <Label id="descripptionLabel" for="gift-item-descripption">
                  Descripption
                </Label>
                <AvField id="gift-item-descripption" type="text" name="descripption" />
              </AvGroup>
              <AvGroup>
                <Label id="unitPriceLabel" for="gift-item-unitPrice">
                  Unit Price
                </Label>
                <AvField id="gift-item-unitPrice" type="string" className="form-control" name="unitPrice" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/gift-item" replace color="info">
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
  giftItemEntity: storeState.giftItem.entity,
  loading: storeState.giftItem.loading,
  updating: storeState.giftItem.updating,
  updateSuccess: storeState.giftItem.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GiftItemUpdate);