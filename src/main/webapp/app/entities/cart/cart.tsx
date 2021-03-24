import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './cart.reducer';
import { ICart } from 'app/shared/model/cart.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICartProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Cart = (props: ICartProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { cartList, match, loading } = props;
  return (
    <div>
      <h2 id="cart-heading">
        Carts
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Cart
        </Link>
      </h2>
      <div className="table-responsive">
        {cartList && cartList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Descripption</th>
                <th>Gift Item</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cartList.map((cart, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${cart.id}`} color="link" size="sm">
                      {cart.id}
                    </Button>
                  </td>
                  <td>{cart.descripption}</td>
                  <td>{cart.giftItem ? <Link to={`gift-item/${cart.giftItem.id}`}>{cart.giftItem.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${cart.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${cart.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${cart.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Carts found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ cart }: IRootState) => ({
  cartList: cart.entities,
  loading: cart.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Cart);
