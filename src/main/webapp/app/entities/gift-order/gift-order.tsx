import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './gift-order.reducer';
import { IGiftOrder } from 'app/shared/model/gift-order.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGiftOrderProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const GiftOrder = (props: IGiftOrderProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { giftOrderList, match, loading } = props;
  return (
    <div>
      <h2 id="gift-order-heading">
        Gift Orders
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Gift Order
        </Link>
      </h2>
      <div className="table-responsive">
        {giftOrderList && giftOrderList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Descripption</th>
                <th>User</th>
                <th>Gift Items</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {giftOrderList.map((giftOrder, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${giftOrder.id}`} color="link" size="sm">
                      {giftOrder.id}
                    </Button>
                  </td>
                  <td>{giftOrder.descripption}</td>
                  <td>{giftOrder.user ? giftOrder.user.id : ''}</td>
                  <td>
                    {giftOrder.giftItems
                      ? giftOrder.giftItems.map((val, j) => (
                          <span key={j}>
                            <Link to={`gift-item/${val.id}`}>{val.id}</Link>
                            {j === giftOrder.giftItems.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${giftOrder.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${giftOrder.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${giftOrder.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Gift Orders found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ giftOrder }: IRootState) => ({
  giftOrderList: giftOrder.entities,
  loading: giftOrder.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GiftOrder);
