using Microsoft.EntityFrameworkCore;

namespace DB
{
    public class AppDbContext : DbContext
    {
        
            public DbSet<Todos> Todo { get; set; }

            protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
            {
                optionsBuilder.UseNpgsql(
                    @"Host=localhost;Database=TODO;Username=postgres;Password=123");
           
            }
    }
}
