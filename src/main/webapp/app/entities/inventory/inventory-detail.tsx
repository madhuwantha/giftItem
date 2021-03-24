import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './inventory.reducer';
import { IInventory } from 'app/shared/model/inventory.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInventoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InventoryDetail = (props: IInventoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { inventoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Inventory [<b>{inventoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="avalibleQuantity">Avalible Quantity</span>
          </dt>
          <dd>{inventoryEntity.avalibleQuantity}</dd>
        </dl>
        <Button tag={Link} to="/inventory" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inventory/${inventoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ inventory }: IRootState) => ({
  inventoryEntity: inventory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InventoryDetail);
