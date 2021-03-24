import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './iinventory.reducer';
import { IIinventory } from 'app/shared/model/iinventory.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIinventoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const IinventoryDetail = (props: IIinventoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { iinventoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Iinventory [<b>{iinventoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="avalibleQuantity">Avalible Quantity</span>
          </dt>
          <dd>{iinventoryEntity.avalibleQuantity}</dd>
        </dl>
        <Button tag={Link} to="/iinventory" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/iinventory/${iinventoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ iinventory }: IRootState) => ({
  iinventoryEntity: iinventory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IinventoryDetail);
