using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Script.Serialization;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class _Handler : System.Web.UI.Page
{
    dynamic jsonObject;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Request.HttpMethod == "GET") return;
        JavaScriptSerializer serializer = new JavaScriptSerializer();
        Response.StatusCode = (int)HttpStatusCode.OK;
        Response.ContentType = "application/json";
        if (!String.IsNullOrWhiteSpace(Request.Form["Content"]))
        {
            try
            {
                jsonObject = serializer.Deserialize<Dictionary<string, object>>(Request.Form["Content"]);
                if (jsonObject["method"] == "InsertGPSdatas")
                {
                    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["itumapdb"].ConnectionString);
                    String insertQuery = "INSERT INTO [GPSData] (imei, latitude, longtitude, timestamp, out) VALUES (@imei, @latitude, @longtitude, @timestamp, @out)";
                    SqlCommand insertCmd = new SqlCommand(insertQuery, con);
                    insertCmd.Parameters.AddWithValue("@imei", jsonObject["parameters"]["imei"]);
                    insertCmd.Parameters.AddWithValue("@latitude", jsonObject["parameters"]["latitude"]);
                    insertCmd.Parameters.AddWithValue("@longtitude", jsonObject["parameters"]["longitude"]);
                    insertCmd.Parameters.AddWithValue("@timestamp", DateTime.ParseExact(jsonObject["parameters"]["timestamp"], "dd-MM-yyyy HH:mm:ss", System.Globalization.CultureInfo.InvariantCulture));
                    insertCmd.Parameters.AddWithValue("@out", jsonObject["parameters"]["out"]);
                    try
                    {
                        if (con.State == ConnectionState.Closed)
                            con.Open();
                        insertCmd.ExecuteNonQuery();
                    }
                    catch (Exception ex)
                    {
                        Response.Write(ex.Message);
                        Response.StatusCode = (int)HttpStatusCode.InternalServerError;
                    }
                    finally
                    {
                        con.Close();
                    }
                }
                else if (jsonObject["method"] == "getMarks")
                {
                    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["itumapdb"].ConnectionString);
                    String selectQuery = "Select * from GPSData as I1 where ID in (Select top 1 ID from GPSData as I2 where I2.imei=I1.imei order by ID desc) and out='0' and timestamp > dateadd(day ,-1,getdate())";
                    SqlCommand selectCmd = new SqlCommand(selectQuery, con);
                    try
                    {
                        if (con.State == ConnectionState.Closed)
                            con.Open();
                        SqlDataReader selectResult = selectCmd.ExecuteReader();
                        var marks = new Dictionary<string, object>();
                        while (selectResult.Read())
                        {
                            marks.Add(selectResult["imei"].ToString(), new Dictionary<string, object>{
                                { "imei", selectResult["imei"].ToString() },
                                { "latitude", selectResult["latitude"].ToString() },
                                { "longitude", selectResult["longtitude"].ToString() },
                                { "timestamp", Convert.ToDateTime(selectResult["timestamp"]).ToString("dd-MM-yyyy HH:mm:ss") }
                            });
                        }
                        //var getMarks = new Dictionary<string, object> { { "getMarks", marks } };
                        Response.Write(serializer.Serialize(marks));
                    }
                    catch (Exception ex)
                    {
                        Response.Write(ex.Message);
                        Response.StatusCode = (int)HttpStatusCode.InternalServerError;
                    }
                    finally
                    {
                        con.Close();
                    }
                }
                else if (jsonObject["method"] == "getOwnRoutes")
                {
                    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["itumapdb"].ConnectionString);
                    String selectQuery = "Select * from GPSData where (imei = @imei and timestamp >= @mindate and timestamp <= @maxdate) order by ID asc";
                    SqlCommand selectCmd = new SqlCommand(selectQuery, con);
                    selectCmd.Parameters.AddWithValue("@imei", jsonObject["parameters"]["imei"]);
                    selectCmd.Parameters.AddWithValue("@mindate", DateTime.ParseExact(jsonObject["parameters"]["mindate"], "dd-MM-yyyy HH:mm:ss", System.Globalization.CultureInfo.InvariantCulture));
                    selectCmd.Parameters.AddWithValue("@maxdate", DateTime.ParseExact(jsonObject["parameters"]["maxdate"], "dd-MM-yyyy HH:mm:ss", System.Globalization.CultureInfo.InvariantCulture));
                    try
                    {
                        if (con.State == ConnectionState.Closed)
                            con.Open();
                        SqlDataReader selectResult = selectCmd.ExecuteReader();
                        var routes = new Dictionary<string, object>();
                        while (selectResult.Read())
                        {
                            routes.Add(selectResult["ID"].ToString(), new Dictionary<string, object>{
                                { "latitude", selectResult["latitude"].ToString() },
                                { "longitude", selectResult["longtitude"].ToString() },
                                { "timestamp", Convert.ToDateTime(selectResult["timestamp"]).ToString("dd-MM-yyyy HH:mm:ss") },
                                { "out", selectResult["out"].ToString() }
                            });
                        }
                        //var getOwnRoutes = new Dictionary<string, object> { {"getOwnRoutes", routes }};
                        Response.Write(serializer.Serialize(routes));
                    }
                    catch (Exception ex)
                    {
                        Response.Write(ex.Message);
                        Response.StatusCode = (int)HttpStatusCode.InternalServerError;
                    }
                    finally
                    {
                        con.Close();
                    }
                }
                else if (jsonObject["method"] == "getRoutes")
                {
                    SqlConnection con = new SqlConnection("Data Source=itumapdb.mssql.somee.com;Initial Catalog=itumapdb;Integrated Security=False;User ID=grad_SQLLogin_1;Password=grad_SQLLogin_1;Connect Timeout=15;Encrypt=False;Packet Size=4096");
                    SqlConnection con2 = new SqlConnection("Data Source=itumapdb.mssql.somee.com;Initial Catalog=itumapdb;Integrated Security=False;User ID=grad_SQLLogin_1;Password=grad_SQLLogin_1;Connect Timeout=15;Encrypt=False;Packet Size=4096");
                    String selectImei = "select imei from GPSData group by imei";
                    String selectQuery = "Select * from GPSData where (imei = @imei) order by ID asc";
                    SqlCommand selectCmd = new SqlCommand(selectImei, con);
                    try
                    {
                        if (con.State == ConnectionState.Closed)
                            con.Open();
                        SqlDataReader selectImeiResult = selectCmd.ExecuteReader();
                        var imei = new Dictionary<string, object>();
                        while (selectImeiResult.Read())
                        {
                            SqlCommand selectCmd2 = new SqlCommand(selectQuery, con2);
                            selectCmd2.Parameters.AddWithValue("@imei", selectImeiResult["imei"].ToString());
                            if (con2.State == ConnectionState.Closed)
                                con2.Open();
                            SqlDataReader selectResult = selectCmd2.ExecuteReader();
                            var routes = new Dictionary<string, object>();
                            while (selectResult.Read())
                            {
                                routes.Add(selectResult["ID"].ToString(), new Dictionary<string, object>{
                                    { "latitude", selectResult["latitude"].ToString() },
                                    { "longitude", selectResult["longtitude"].ToString() },
                                    { "timestamp", Convert.ToDateTime(selectResult["timestamp"]).ToString("dd-MM-yyyy HH:mm:ss") },
                                    { "out", selectResult["out"].ToString() }
                                });
                            }
                            selectResult.Close();
                            imei.Add(selectImeiResult["imei"].ToString(), routes);
                        }
                        Response.Write(serializer.Serialize(imei));
                    }
                    catch (Exception ex)
                    {
                        Response.Write(ex.Message);
                        Response.StatusCode = (int)HttpStatusCode.InternalServerError;
                    }
                    finally
                    {
                        con.Close();
                        con2.Close();
                    }
                }
                else if (jsonObject["method"] == "login")
                {      
                    try
                    {
                        if (jsonObject["parameters"]["username"] == "admin" && jsonObject["parameters"]["password"] == "admin123")
                            Response.Write(serializer.Serialize(new Dictionary<string, string> { { "status", "successful" } }));
                        else
                            Response.Write(serializer.Serialize(new Dictionary<string, string> { { "status", "failed" } }));     
                    }
                    catch (Exception ex)
                    {
                        Response.Write(ex.Message);
                        Response.StatusCode = (int)HttpStatusCode.InternalServerError;
                    }
                }
            }
            catch (Exception ex)
            {
                //If JSON Deserializing failed
                Response.StatusCode = (int)HttpStatusCode.NotAcceptable;
            }
        }
        else
            Response.StatusCode = (int)HttpStatusCode.BadRequest;
    }
    protected override void Render(System.Web.UI.HtmlTextWriter writer)
    {
        if (Request.HttpMethod == "GET")
            base.Render(writer);
    }
}
