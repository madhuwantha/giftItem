import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './gift-order.reducer';
import { IGiftOrder } from 'app/shared/model/gift-order.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGiftOrderDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GiftOrderDetail = (props: IGiftOrderDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { giftOrderEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          GiftOrder [<b>{giftOrderEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descripption">Descripption</span>
          </dt>
          <dd>{giftOrderEntity.descripption}</dd>
          <dt>User</dt>
          <dd>{giftOrderEntity.user ? giftOrderEntity.user.id : ''}</dd>
          <dt>Gift Items</dt>
          <dd>
            {giftOrderEntity.giftItems
              ? giftOrderEntity.giftItems.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {giftOrderEntity.giftItems && i === giftOrderEntity.giftItems.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/gift-order" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gift-order/${giftOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ giftOrder }: IRootState) => ({
  giftOrderEntity: giftOrder.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GiftOrderDetail);
