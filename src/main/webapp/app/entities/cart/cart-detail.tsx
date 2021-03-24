import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cart.reducer';
import { ICart } from 'app/shared/model/cart.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICartDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CartDetail = (props: ICartDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cartEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Cart [<b>{cartEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descripption">Descripption</span>
          </dt>
          <dd>{cartEntity.descripption}</dd>
          <dt>Gift Item</dt>
          <dd>{cartEntity.giftItem ? cartEntity.giftItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cart" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cart/${cartEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cart }: IRootState) => ({
  cartEntity: cart.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CartDetail);
